import { useViewConfig } from '@vaadin/hilla-file-router/runtime.js';
import { effect, signal } from '@vaadin/hilla-react-signals';
import { Suspense, useEffect } from 'react';
import { Outlet } from 'react-router';

const documentTitleSignal = signal('');
effect(() => {
    document.title = documentTitleSignal.value;
});

// Publish for Vaadin to use
// eslint-disable-next-line @typescript-eslint/no-explicit-any
(window as any).Vaadin.documentTitleSignal = documentTitleSignal;

export default function MainLayout() {
    const currentTitle = useViewConfig()?.title;

    useEffect(() => {
        if (currentTitle) {
            documentTitleSignal.value = currentTitle;
        }
    }, [currentTitle]);

    return (
        <div className="flex min-h-screen w-full flex-col">
            <Suspense>
                <Outlet />
            </Suspense>
        </div>
    );
}
