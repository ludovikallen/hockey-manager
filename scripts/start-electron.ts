import { spawn } from 'child_process';
import path from 'path';

// Spawn Electron in a detached process
function startElectronDetached() {
    console.log('starting electron');
    const electronPath = path.join(process.cwd(), 'node_modules', '.bin', 'electron');
    console.log(electronPath);
    const projectRoot = process.cwd();

    const child = spawn(electronPath, ['.'], {
        detached: true,
        stdio: 'ignore',
        cwd: projectRoot,
    });
    console.log(child);

    child.unref();
}

// Run the function
startElectronDetached();
