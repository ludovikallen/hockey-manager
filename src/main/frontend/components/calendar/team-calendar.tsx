import { format, parseISO } from 'date-fns'; // Using date-fns for easy date formatting
import { useMemo } from 'react';

import { Badge } from '@/components/ui/badge';
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from '@/components/ui/card'; // Adjust path as needed
import { Separator } from '@/components/ui/separator';
import Game from '@/generated/com/hockeymanager/application/schedules/models/Game';
import Team from '@/generated/com/hockeymanager/application/teams/models/Team';
import { ScrollArea } from '../ui/scroll-area';

type GameResult = {
    gameId: string;
    homeScore: number;
    awayScore: number;
};

type ProcessedGame = Game & {
    isPlayed: boolean;
    isHomeGame: boolean;
    opponent: Team;
    result?: 'W' | 'L' | 'T';
    score?: string;
};

interface TeamScheduleProps {
    userTeam: Team;
    allGames: Game[];
    gameResults: GameResult[];
    maxPastGames?: number;
    maxUpcomingGames?: number;
}

function getResultInfo(game: Game, result: GameResult, userTeam: Team): { result: 'W' | 'L' | 'T'; score: string } {
    const isHome = game.homeTeam!.id === userTeam.id;
    const userScore = isHome ? result.homeScore : result.awayScore;
    const opponentScore = isHome ? result.awayScore : result.homeScore;
    let gameResult: 'W' | 'L' | 'T';

    if (userScore > opponentScore) {
        gameResult = 'W';
    } else if (userScore < opponentScore) {
        gameResult = 'L';
    } else {
        gameResult = 'T';
    }

    const scoreString = `${userScore}-${opponentScore}`;

    return { result: gameResult, score: scoreString };
}

export function TeamSchedule({
    userTeam,
    allGames,
    gameResults,
    maxPastGames = 3,
    maxUpcomingGames = 5,
}: TeamScheduleProps) {
    const processedGames = useMemo(() => {
        const resultsMap = new Map(gameResults.map((r) => [r.gameId, r]));

        return allGames
            .filter((game) => game.homeTeam!.id === userTeam.id || game.awayTeam!.id === userTeam.id)
            .sort((a, b) => new Date(a.date!).getTime() - new Date(b.date!).getTime())
            .map((game): ProcessedGame => {
                const resultData = resultsMap.get(game.id!);
                const isPlayed = !!resultData;
                const isHomeGame = game.homeTeam!.id === userTeam.id;
                const opponent = isHomeGame ? game.awayTeam : game.homeTeam;
                let resultInfo: { result: 'W' | 'L' | 'T'; score: string } | undefined = undefined;

                if (isPlayed && resultData) {
                    resultInfo = getResultInfo(game, resultData, userTeam);
                }

                return {
                    ...game,
                    isPlayed,
                    isHomeGame,
                    opponent: opponent!,
                    result: resultInfo?.result,
                    score: resultInfo?.score,
                };
            });
    }, [allGames, gameResults, userTeam]);

    const pastGames = processedGames.filter((g) => g.isPlayed).slice(-maxPastGames);
    const upcomingGames = processedGames.filter((g) => !g.isPlayed).slice(0, maxUpcomingGames);

    const renderGameItem = (game: ProcessedGame) => (
        <div id={game.id!} className="flex justify-between items-center py-1 text-sm">
            <div className="flex items-center space-x-2">
                <span className="w-12 text-muted-foreground font-medium">{format(parseISO(game.date!), 'MMM d')}</span>
                <span>{game.isHomeGame ? 'vs' : '@'}</span>
                <span className="font-semibold">{game.opponent.abbreviation}</span>
            </div>

            <div>
                {game.isPlayed && game.result && game.score ? (
                    <Badge
                        variant={game.result === 'W' ? 'default' : game.result === 'L' ? 'destructive' : 'secondary'}
                        className="w-16 justify-center">
                        {game.result} {game.score}
                    </Badge>
                ) : (
                    <Badge variant="outline" className="w-16 justify-center text-muted-foreground">
                        -
                    </Badge>
                )}
            </div>
        </div>
    );

    return (
        <Card className="w-full">
            {' '}
            <CardHeader className="p-3">
                {' '}
                <CardTitle className="text-lg"> Game Schedule</CardTitle>
                <CardDescription>Recent results and upcoming games</CardDescription>
            </CardHeader>
            <CardContent className="p-3 text-sm">
                <ScrollArea className="h-64">
                    {' '}
                    {pastGames.length > 0 && (
                        <>
                            <h4 className="text-xs uppercase text-muted-foreground mb-1">Recent Results</h4>
                            {pastGames.map(renderGameItem)}
                            {upcomingGames.length > 0 && <Separator className="my-2" />}
                        </>
                    )}
                    {upcomingGames.length > 0 && (
                        <>
                            <h4 className="text-xs uppercase text-muted-foreground mb-1">Upcoming Games</h4>
                            {upcomingGames.map(renderGameItem)}
                        </>
                    )}
                    {pastGames.length === 0 && upcomingGames.length === 0 && (
                        <p className="text-muted-foreground text-center py-4">No games scheduled.</p>
                    )}
                </ScrollArea>
            </CardContent>
        </Card>
    );
}
