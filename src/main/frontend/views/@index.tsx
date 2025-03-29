import CreatingDynastyDialog from '@/components/dynasty/creating-dynasty-dialog';
import LoadingDynastyDialog from '@/components/dynasty/loading-dynasty-dialog';
import { RetroGrid } from '@/components/ui/retro-grid';
import { ViewConfig } from '@vaadin/hilla-file-router/types.js';
import { useState } from 'react';
import MainGameView from './dynasty';

export const config: ViewConfig = {
    menu: { order: 0, icon: 'line-awesome/svg/globe-solid.svg' },
    title: 'Main Menu',
};

export default function MainMenuView() {
    const [dynastyId, setDynastyId] = useState<string | undefined>(undefined);

    if (dynastyId !== undefined) {
        return <MainGameView dynastyId={dynastyId} setDynastyId={setDynastyId} />;
    }

    return (
        <div className="relative flex h-screen w-full flex-col items-center justify-center overflow-hidden rounded-lg border bg-background gap-8">
            <span className="p-10 pointer-events-none z-10 whitespace-pre-wrap text-center text-7xl font-bold leading-none tracking-tighter">
                Hockey Manager
            </span>
            <div className="flex flex-col gap-8 w-60">
                <CreatingDynastyDialog setDynastyId={setDynastyId} />
                <LoadingDynastyDialog setDynastyId={setDynastyId} />
            </div>

            <RetroGrid />
        </div>
    );
}
