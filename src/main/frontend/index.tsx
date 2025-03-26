import { router } from 'Frontend/generated/routes.js';
import { createElement } from 'react';
import { createRoot } from 'react-dom/client';

import { RouterProvider } from 'react-router/dom';
import { Toaster } from './components/ui/sonner';
import './index.css';

function App() {
    return (
        <>
            <RouterProvider router={router} />
            <Toaster />
        </>
    );
}

const outlet = document.getElementById('outlet')!;
// eslint-disable-next-line @typescript-eslint/no-explicit-any
const root = (outlet as any)._root ?? createRoot(outlet);
// eslint-disable-next-line @typescript-eslint/no-explicit-any
(outlet as any)._root = root;
root.render(createElement(App));
