import tailwindcss from '@tailwindcss/vite';
import { exec } from 'child_process';
import path from 'path';
import { UserConfigFn } from 'vite';
import { overrideVaadinConfig } from './vite.generated';

const customConfig: UserConfigFn = (configEnv) => {
    if (configEnv.command === 'serve' && process.env.NODE_ENV === 'development') {
        const electronPath = path.join(process.cwd(), 'node_modules', '.bin', 'electron');

        exec(electronPath + ' .');
    }

    return {
        plugins: [tailwindcss()],
        resolve: {
            alias: {
                '@': path.resolve(__dirname, './src/main/frontend'),
            },
        },
    };
};

export default overrideVaadinConfig(customConfig);
