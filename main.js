import { spawn } from 'child_process';
import { app, BrowserWindow } from 'electron';
import fs from 'fs';
import http from 'http';
import path from 'path';
import { fileURLToPath } from 'url';

const __filename = fileURLToPath(import.meta.url);
const __dirname = path.dirname(__filename);

const isDev = !app.isPackaged;

let mainWindow = null;
let loadingWindow = null;
let jarProcess = null;
const url = 'http://localhost:8080';

const userDataPath = app.getPath('userData');
console.log(`Application data directory: ${userDataPath}`);

const dbFolder = path.join(userDataPath, 'database');
if (!fs.existsSync(dbFolder)) {
    fs.mkdirSync(dbFolder, { recursive: true });
}

const dbFilePath = path.join(dbFolder, 'hockeymanager.sqlite');

function createLoadingWindow() {
    loadingWindow = new BrowserWindow({
        width: 400,
        height: 300,
        frame: false,
        transparent: true,
        webPreferences: {
            nodeIntegration: true,
            contextIsolation: false,
        },
    });

    loadingWindow.removeMenu();
    loadingWindow.maximize();
    loadingWindow.setFullScreen(true);

    loadingWindow.loadFile(path.join(__dirname, 'loading.html'));

    loadingWindow.on('closed', () => {
        loadingWindow = null;
    });
}

function createMainWindow() {
    mainWindow = new BrowserWindow({
        width: 800,
        height: 600,
        show: false,
        webPreferences: {
            nodeIntegration: true,
            contextIsolation: false,
        },
    });

    mainWindow.maximize();
    mainWindow.setFullScreen(true);

    if (!isDev) {
        mainWindow.removeMenu();
    } else {
        mainWindow.setMenuBarVisibility(true);
        mainWindow.setAutoHideMenuBar(false);
    }
    mainWindow.loadURL(url);

    mainWindow.once('ready-to-show', () => {
        if (loadingWindow) {
            loadingWindow.close();
        }
        mainWindow.show();
    });

    mainWindow.on('closed', () => {
        mainWindow = null;
    });
}

function startSpringBootApp() {
    const jarPath = path.join(process.resourcesPath, 'app.jar');

    const javaArgs = [`-Dspring.datasource.url=jdbc:sqlite:=${dbFilePath.replace(/\\/g, '\\\\')}`, '-jar', jarPath];

    console.log(`Looking for JAR at: ${jarPath}`);

    if (!fs.existsSync(jarPath)) {
        console.error(`JAR file not found at ${jarPath}`);
        return;
    }

    console.log('Starting Spring Boot application...');

    jarProcess = spawn('java', javaArgs);

    jarProcess.stdout.on('data', (data) => {
        console.log(`Spring Boot: ${data}`);
    });

    jarProcess.stderr.on('data', (data) => {
        console.error(`Spring Boot Error: ${data}`);
    });

    jarProcess.on('close', (code) => {
        console.log(`Spring Boot process exited with code ${code}`);
        if (code !== 0 && !app.isQuitting) {
            console.error('Spring Boot application terminated unexpectedly');
            if (loadingWindow) {
                loadingWindow.webContents.executeJavaScript(
                    `document.getElementById("loading-text").innerHTML = "Error: Spring Boot exited with code ${code}"`,
                );
            }
        }
    });
}

function checkUrlReady() {
    http.get(url, (response) => {
        if (response.statusCode === 200) {
            createMainWindow();
        } else {
            setTimeout(checkUrlReady, 1000);
        }
    }).on('error', (err) => {
        setTimeout(checkUrlReady, 1000);
    });
}

app.whenReady().then(() => {
    createLoadingWindow();

    // In development, we assume the Spring Boot app is already running
    // In production, we need to start it ourselves
    if (!isDev) {
        startSpringBootApp();
    }

    checkUrlReady();
});

app.on('window-all-closed', () => {
    if (process.platform !== 'darwin') {
        app.quit();
    }
});

app.on('activate', () => {
    if (mainWindow === null && loadingWindow === null) {
        createLoadingWindow();
        checkUrlReady();
    }
});

app.on('before-quit', () => {
    app.isQuitting = true;
    if (jarProcess) {
        jarProcess.kill();
    }
});
