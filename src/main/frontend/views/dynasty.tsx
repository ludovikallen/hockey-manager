import { Button } from '@/components/ui/button';
import Dynasty from '@/generated/com/hockeymanager/application/dynasties/models/Dynasty';
import Goalie from '@/generated/com/hockeymanager/application/players/models/Goalie';
import Player from '@/generated/com/hockeymanager/application/players/models/Player';
import { DynastiesService, GoaliesService, PlayersService } from '@/generated/endpoints';
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
    const [bestPlayer, setBestPlayer] = useState<Player | undefined>(undefined);
    const [bestGoalie, setBestGoalie] = useState<Goalie | undefined>(undefined);

    useEffect(() => {
        const fetchDynasty = async () => {
            const response = await DynastiesService.findById(dynastyId);
            const currentPlayers = await PlayersService.findAllByTeamId(response?.team?.id);
            const currentGoalies = await GoaliesService.findAllByTeamId(response?.team?.id);
            const bestPlayer = currentPlayers?.sort((a, b) => b.currentAbility - a.currentAbility)[0];
            const bestGoalie = currentGoalies?.sort((a, b) => b.currentAbility - a.currentAbility)[0];

            setDynasty(response!);
            setBestPlayer(bestPlayer);
            setBestGoalie(bestGoalie);
        };

        fetchDynasty();
    }, []);

    if (dynasty === undefined) {
        return <div className="m-auto">Loading...</div>;
    }

    return (
        <div className="relative flex h-screen w-full flex-col items-center justify-center overflow-hidden rounded-lg border bg-background gap-8">
            <Button
                className="absolute top-4 left-4"
                variant="outline"
                onClick={() => {
                    setDynastyId(undefined);
                }}>
                <ArrowLeftIcon className="mr-2 h-4 w-4" />
                Back to Main Menu
            </Button>
            <span className="p-10 pointer-events-none z-10 whitespace-pre-wrap text-center text-7xl font-bold leading-none tracking-tighter">
                {dynasty.name}
            </span>
            <div className="flex flex-col gap-8">
                <h1>Dynasty Details</h1>
                <p>Team Name: {dynasty.team?.name}</p>
                <p>Team Abbreviation: {dynasty.team?.abbreviation}</p>
                <p>
                    Best Player: {bestPlayer?.firstName} {bestPlayer?.lastName}
                </p>
                <p>
                    Best Goalie: {bestGoalie?.firstName} {bestGoalie?.lastName}
                </p>
            </div>
        </div>
    );
}
