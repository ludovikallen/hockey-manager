import { format, parseISO } from 'date-fns';

import { Badge } from '@/components/ui/badge';
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from '@/components/ui/card'; // Adjust path as needed
import { Separator } from '@/components/ui/separator';
import { ProcessedGame } from '@/views/dynasty';
import { ScrollArea } from '../ui/scroll-area';

interface TeamScheduleProps {
    processedGames: ProcessedGame[];
    maxPastGames?: number;
    maxUpcomingGames?: number;
    currentDate: string;
}

export function TeamSchedule({
    processedGames,
    maxPastGames = 82,
    maxUpcomingGames = 82,
    currentDate,
}: TeamScheduleProps) {
    const pastGames = processedGames.filter((g) => g.isPlayed).slice(-maxPastGames);
    const upcomingGames = processedGames.filter((g) => !g.isPlayed).slice(0, maxUpcomingGames);

    const renderGameItem = (game: ProcessedGame) => (
        <div id={game.id!} className="flex justify-between items-center py-1 text-sm">
            <div className="flex items-center space-x-2">
                <span className="w-12 text-muted-foreground font-medium">{format(parseISO(game.date!), 'MMM d')}</span>
                <span>{game.isHomeGame ? 'vs' : '@'}</span>
                <span className="font-semibold">{game.opponent.name}</span>
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
        <Card className="w-full h-80">
            {' '}
            <CardHeader className="p-3">
                {' '}
                <CardTitle className="flex items-center justify-between">
                    <div>Game Schedule</div>
                    <div className="text-sm text-muted-foreground">{format(parseISO(currentDate), 'MMMM d, yyyy')}</div>
                </CardTitle>
                <CardDescription>Recent results and upcoming games</CardDescription>
            </CardHeader>
            <CardContent className="p-3 text-sm">
                <ScrollArea className="h-52">
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
