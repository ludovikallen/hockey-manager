import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { ProcessedGame } from '@/views/dynasty';
import { BarChart3, TrendingUp, Trophy } from 'lucide-react';
import { useEffect, useState } from 'react';

export interface TeamStats {
    record: string;
    points: number;
    pointPercentage: number;
    goalsFor: number;
    goalsAgainst: number;
    gamesPlayed: number;
    homeRecord: string;
    awayRecord: string;
    last10Record: string;
    streak: string;
}

interface PerformanceSectionProps {
    processedGames: ProcessedGame[];
}

function getStreak(processedGames: ProcessedGame[]): string {
    const lastGame = processedGames[processedGames.length - 1];
    if (!lastGame || lastGame.result == null) return '-';

    let streakLength = 0;
    for (let i = processedGames.length - 1; i >= 0; i--) {
        if (processedGames[i].result === lastGame.result) {
            streakLength++;
        } else {
            break;
        }
    }

    return `${streakLength > 1 ? streakLength : ''}${lastGame.result ?? '-'}`;
}

export function PerformanceSection({ processedGames }: PerformanceSectionProps) {
    const [teamStats, setTeamStats] = useState<TeamStats>();
    useEffect(() => {
        const calculateTeamStats = () => {
            const wins = processedGames.filter((result) => result.result === 'W').length;
            const losses = processedGames.filter((result) => result.result === 'L').length;

            const totalGames = wins + losses;
            const points = wins * 2;
            const pointPercentage = totalGames > 0 ? points / (totalGames * 2) : 0;

            const goalsFor = processedGames.reduce((sum, result) => sum + (result.userScore || 0), 0);
            const goalsAgainst = processedGames.reduce((sum, result) => sum + (result.opponentScore || 0), 0);

            const homeGames = processedGames.filter((game) => game.isHomeGame);
            const homeWins = homeGames.filter((result) => result.result === 'W').length;
            const homeLosses = homeGames.filter((result) => result.result === 'L').length;

            const awayGames = processedGames.filter((game) => !game.isHomeGame);
            const awayWins = awayGames.filter((result) => result.result === 'W').length;
            const awayLosses = awayGames.filter((result) => result.result === 'L').length;

            const last10Games = processedGames.filter((game) => game.result != undefined).slice(-10);
            const last10Wins = last10Games.filter((result) => result.result === 'W').length;
            const last10Losses = last10Games.filter((result) => result.result === 'L').length;

            const homeRecord = `${homeWins}-${homeLosses}`;
            const awayRecord = `${awayWins}-${awayLosses}`;
            const last10Record = `${last10Wins}-${last10Losses}`;

            const streak = getStreak(processedGames.filter((game) => game.result != undefined));

            setTeamStats({
                record: `${wins}-${losses}`,
                points,
                pointPercentage,
                goalsFor,
                goalsAgainst,
                gamesPlayed: totalGames,
                homeRecord,
                awayRecord,
                last10Record,
                streak,
            });
        };

        calculateTeamStats();
    }, [processedGames]);

    if (!teamStats) {
        return (
            <Card className="lg:col-span-2">
                <CardHeader>
                    <CardTitle>Team Performance Overview</CardTitle>
                </CardHeader>
                <CardContent>
                    <div className="text-sm text-muted-foreground">Loading...</div>
                </CardContent>
            </Card>
        );
    }

    return (
        <Card className="lg:col-span-2 h-80">
            <CardHeader>
                <CardTitle>Team Performance Overview</CardTitle>
            </CardHeader>
            <CardContent>
                <div className="grid gap-4 grid-cols-1 md:grid-cols-3">
                    <div className="space-y-2">
                        <div className="flex items-center gap-2">
                            <Trophy className="h-5 w-5 text-amber-500" />
                            <h3 className="text-lg font-semibold">Team Record</h3>
                        </div>
                        <div className="text-2xl font-bold">{teamStats.record}</div>
                        <div className="mt-2 text-sm">
                            <span className="font-medium">{teamStats.points}</span> points
                            {teamStats.gamesPlayed != 0 && '(' + teamStats.pointPercentage.toFixed(3) + ')'}
                        </div>
                        <p className="mt-1 text-xs text-muted-foreground">{teamStats.gamesPlayed} of 82 games played</p>
                    </div>

                    <div className="space-y-2">
                        <div className="flex items-center gap-2">
                            <TrendingUp className="h-5 w-5 text-blue-500" />
                            <h3 className="text-lg font-semibold">Goals</h3>
                        </div>
                        <div className="text-2xl font-bold">
                            {teamStats.goalsFor} / {teamStats.goalsAgainst}
                        </div>
                        <p className="text-xs text-muted-foreground">
                            Differential: {teamStats.goalsFor - teamStats.goalsAgainst}
                        </p>
                        <div className="mt-2 grid grid-cols-3 gap-2 text-sm">
                            <div>
                                <div className="font-medium">
                                    {teamStats.gamesPlayed == 0
                                        ? '-'
                                        : (teamStats.goalsFor / teamStats.gamesPlayed).toFixed(2)}
                                </div>
                                <div className="text-xs text-muted-foreground">Goals/Game</div>
                            </div>
                            <div>
                                <div className="font-medium">
                                    {teamStats.gamesPlayed == 0
                                        ? '-'
                                        : (teamStats.goalsAgainst / teamStats.gamesPlayed).toFixed(2)}
                                </div>
                                <div className="text-xs text-muted-foreground">GA/Game</div>
                            </div>
                            <div>
                                <div className="font-medium">
                                    {teamStats.gamesPlayed == 0
                                        ? '-'
                                        : (
                                              (teamStats.goalsFor - teamStats.goalsAgainst) /
                                              teamStats.gamesPlayed
                                          ).toFixed(2)}
                                </div>
                                <div className="text-xs text-muted-foreground">+/- Per Game</div>
                            </div>
                        </div>
                    </div>

                    <div className="space-y-2">
                        <div className="flex items-center gap-2">
                            <BarChart3 className="h-5 w-5 text-green-500" />
                            <h3 className="text-lg font-semibold">Team Stats</h3>
                        </div>
                        <div className="grid grid-cols-2 gap-4">
                            <div>
                                <div className="text-xs text-muted-foreground">Home</div>
                                <div className="text-sm font-medium">{teamStats.homeRecord}</div>
                            </div>
                            <div>
                                <div className="text-xs text-muted-foreground">Away</div>
                                <div className="text-sm font-medium">{teamStats.awayRecord}</div>
                            </div>
                            <div>
                                <div className="text-xs text-muted-foreground">Last 10</div>
                                <div className="text-sm font-medium">{teamStats.last10Record}</div>
                            </div>
                            <div>
                                <div className="text-xs text-muted-foreground">Streak</div>
                                <div className="text-sm font-medium">{teamStats.streak}</div>
                            </div>
                        </div>
                    </div>
                </div>
            </CardContent>
        </Card>
    );
}
