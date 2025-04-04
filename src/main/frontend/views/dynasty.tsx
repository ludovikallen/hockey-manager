import { TeamSchedule } from '@/components/calendar/team-calendar';
import { Button } from '@/components/ui/button';
import Dynasty from '@/generated/com/hockeymanager/application/dynasties/models/Dynasty';
import Game from '@/generated/com/hockeymanager/application/schedules/models/Game';
import { DynastiesService, GamesService } from '@/generated/endpoints';
import { ViewConfig } from '@vaadin/hilla-file-router/types.js';
import { ArrowLeftIcon } from 'lucide-react';
import { useEffect, useState } from 'react';

export const config: ViewConfig = {
    menu: { order: 1, icon: 'line-awesome/svg/globe-solid.svg' },
    title: 'Dynasty',
};

interface MainGameViewProps {
    dynastyId: string;
    setDynastyId: (dynastyId: string | undefined) => void;
}

export default function MainGameView({ dynastyId, setDynastyId }: MainGameViewProps) {
    const [dynasty, setDynasty] = useState<Dynasty | undefined>(undefined);
    const [allGamesInSeason, setAllGamesInSeason] = useState<Game[]>([]);

    useEffect(() => {
        const fetchDynasty = async () => {
            const response = await DynastiesService.findById(dynastyId);
            const games = await GamesService.findAllByTeamId(response?.team?.id);

            setDynasty(response!);
            setAllGamesInSeason(games!);
        };

        fetchDynasty();
    }, []);

    if (dynasty === undefined) {
        return <div className="m-auto">Loading...</div>;
    }

    return (
        <div className="flex h-screen w-full flex-col items-center justify-center overflow-hidden rounded-lg border bg-background gap-8">
            <Button
                className="absolute top-4 left-4"
                variant="outline"
                onClick={() => {
                    setDynastyId(undefined);
                }}>
                <ArrowLeftIcon className="mr-2 h-4 w-4" />
                Back to Main Menu
            </Button>
            <div className="grid grid-cols-4 gap-8 w-full h-full p-8">
                <div className="col-span-3 flex flex-col items-center justify-center" />
                <div className="col-span-1 flex flex-col gap-8 justify-between items-end overflow-hidden">
                    <TeamSchedule userTeam={dynasty.team!} allGames={allGamesInSeason} gameResults={[]} />
                    <div></div>
                </div>
            </div>
        </div>
    );
}
