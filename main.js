import { app, BrowserWindow } from 'electron';
import http from 'http';
import path from 'path';
import { fileURLToPath } from 'url';

// Get __dirname equivalent for ES modules
const __filename = fileURLToPath(import.meta.url);
const __dirname = path.dirname(__filename);

let mainWindow = null;
let loadingWindow = null;
const url = 'http://localhost:8080';

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

  // Load a simple HTML file with your spinner
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

  mainWindow.removeMenu();
  mainWindow.maximize();
  mainWindow.setFullScreen(true);
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

function checkUrlReady() {
  http
    .get(url, (response) => {
      if (response.statusCode === 200) {
        createMainWindow();
      } else {
        setTimeout(checkUrlReady, 1000);
      }
    })
    .on('error', (err) => {
      setTimeout(checkUrlReady, 1000);
    });
}

app.whenReady().then(() => {
  createLoadingWindow();
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
