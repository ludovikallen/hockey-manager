import { TeamSchedule } from '@/components/calendar/team-calendar';
import { LeagueStanding } from '@/components/standing/league-standing';
import { PerformanceSection } from '@/components/standing/performance-section';
import { Button } from '@/components/ui/button';
import Dynasty from '@/generated/com/hockeymanager/application/dynasties/models/Dynasty';
import Game from '@/generated/com/hockeymanager/application/schedules/models/Game';
import GameResult from '@/generated/com/hockeymanager/application/schedules/models/GameResult';
import Team from '@/generated/com/hockeymanager/application/teams/models/Team';
import { DynastiesService, GameResultsService, GamesService } from '@/generated/endpoints';
import { ViewConfig } from '@vaadin/hilla-file-router/types.js';
import { ArrowLeftIcon } from 'lucide-react';
import { useEffect, useMemo, useState } from 'react';

export const config: ViewConfig = {
    menu: { order: 1, icon: 'line-awesome/svg/globe-solid.svg' },
    title: 'Dynasty',
};

export type ProcessedGame = Game & {
    isPlayed: boolean;
    isHomeGame: boolean;
    opponent: Team;
    result?: 'W' | 'L' | 'T';
    score?: string;
    userScore?: number;
    opponentScore?: number;
};

export type LeagueStandings = {
    position: number;
    team: Team;
    gamePlayed: number;
    points: number;
    wins: number;
    losses: number;
    goalsFor: number;
    goalsAgainst: number;
};

interface MainGameViewProps {
    dynastyId: string;
    setDynastyId: (dynastyId: string | undefined) => void;
}

function getResultInfo(
    game: Game,
    result: GameResult,
    userTeam: Team,
): { result: 'W' | 'L' | 'T'; score: string; userScore: number; opponentScore: number } {
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

    return { result: gameResult, score: scoreString, userScore, opponentScore };
}

function getLeagueStandings(
    teams: Team[],
    games: Game[],
    resultsMap: Map<string | undefined, GameResult>,
): LeagueStandings[] {
    const leagueStandings: LeagueStandings[] = [];

    for (let i = 0; i < teams.length; i++) {
        const team = teams[i];
        let gamesPlayed = 0;
        let points = 0;
        let wins = 0;
        let losses = 0;
        let goalsFor = 0;
        let goalsAgainst = 0;
        games
            .filter((game) => game.homeTeam!.id === team.id || game.awayTeam!.id === team.id)
            .sort((a, b) => new Date(a.date!).getTime() - new Date(b.date!).getTime())
            .forEach((game) => {
                const resultData = resultsMap.get(game.id!);
                const isPlayed = !!resultData;
                let resultInfo:
                    | { result: 'W' | 'L' | 'T'; score: string; userScore: number; opponentScore: number }
                    | undefined = undefined;

                if (isPlayed && resultData) {
                    resultInfo = getResultInfo(game, resultData, team);

                    gamesPlayed++;
                    if (resultInfo) {
                        if (resultInfo.result === 'W') {
                            wins++;
                            points += 2;
                        } else if (resultInfo.result === 'L') {
                            losses++;
                        }
                    }

                    goalsFor += resultInfo?.userScore || 0;
                    goalsAgainst += resultInfo?.opponentScore || 0;
                }
            });

        leagueStandings.push({
            position: i + 1,
            team,
            gamePlayed: gamesPlayed,
            points,
            wins,
            losses,
            goalsFor,
            goalsAgainst,
        });
    }

    return leagueStandings;
}

export default function MainGameView({ dynastyId, setDynastyId }: MainGameViewProps) {
    const [dynasty, setDynasty] = useState<Dynasty | undefined>(undefined);
    const [allGamesInSeason, setAllGamesInSeason] = useState<Game[]>([]);
    const [gameResults, setGameResults] = useState<GameResult[]>([]);
    const [leagueStandings, setLeagueStandings] = useState<LeagueStandings[]>([]);

    useEffect(() => {
        const fetchDynasty = async () => {
            const response = await DynastiesService.findById(dynastyId);
            const games = await GamesService.findAllByTeamId(response?.team?.id);
            const results = await GameResultsService.findAllByDynastyId(dynastyId);
            const allGames = await GamesService.findAllByDynastyId(dynastyId);

            const seen = new Set();
            const teams: Team[] = [];
            for (const game of allGames!) {
                if (!seen.has(game.homeTeam?.id)) {
                    seen.add(game.homeTeam?.id);
                    teams.push(game.homeTeam!);
                }
                if (!seen.has(game.awayTeam?.id)) {
                    seen.add(game.awayTeam?.id);
                    teams.push(game.awayTeam!);
                }
            }

            const standings = getLeagueStandings(teams, games!, new Map(results!.map((r) => [r.game?.id, r])));
            setLeagueStandings(standings);

            setDynasty(response!);
            setAllGamesInSeason(games!);
            setGameResults(results!);
        };

        fetchDynasty();
    }, []);

    const processedGames = useMemo(() => {
        if (!dynasty || !allGamesInSeason || !gameResults) {
            return [];
        }

        const resultsMap = new Map(gameResults.map((r) => [r.game?.id, r]));

        return allGamesInSeason
            .filter((game) => game.homeTeam!.id === dynasty.team!.id || game.awayTeam!.id === dynasty.team!.id)
            .sort((a, b) => new Date(a.date!).getTime() - new Date(b.date!).getTime())
            .map((game): ProcessedGame => {
                const resultData = resultsMap.get(game.id!);
                const isPlayed = !!resultData;
                const isHomeGame = game.homeTeam!.id === dynasty.team!.id;
                const opponent = isHomeGame ? game.awayTeam : game.homeTeam;
                let resultInfo:
                    | { result: 'W' | 'L' | 'T'; score: string; userScore: number; opponentScore: number }
                    | undefined = undefined;

                if (isPlayed && resultData) {
                    resultInfo = getResultInfo(game, resultData, dynasty.team!);
                }

                return {
                    ...game,
                    isPlayed,
                    isHomeGame,
                    opponent: opponent!,
                    result: resultInfo?.result,
                    score: resultInfo?.score,
                    userScore: resultInfo?.userScore,
                    opponentScore: resultInfo?.opponentScore,
                };
            });
    }, [allGamesInSeason, gameResults, dynasty]);

    if (dynasty === undefined) {
        return <div className="m-auto">Loading...</div>;
    }

    return (
        <div className="flex h-screen w-full flex-col items-center justify-center overflow-hidden rounded-lg border bg-background gap-8">
            <div className="flex w-full items-center justify-between px-8 py-4">
                <Button
                    variant="outline"
                    onClick={() => {
                        setDynastyId(undefined);
                    }}>
                    <ArrowLeftIcon className="mr-2 h-4 w-4" />
                    Back to Main Menu
                </Button>
            </div>
            <div className="grid grid-cols-4 gap-8 w-full h-full px-8">
                <div className="col-span-3 flex flex-col gap-8 ">
                    <PerformanceSection processedGames={processedGames} />
                    <LeagueStanding leagueStandings={leagueStandings} userTeam={dynasty.team!} />
                </div>
                <div className="col-span-1 flex flex-col gap-8 justify-between items-end overflow-hidden">
                    <TeamSchedule processedGames={processedGames} currentDate={dynasty.currentState!.currentDate!} />
                    <div></div>
                </div>
            </div>
        </div>
    );
}
