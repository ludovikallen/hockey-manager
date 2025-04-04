import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from '@/components/ui/table';
import Team from '@/generated/com/hockeymanager/application/teams/models/Team';
import { LeagueStandings } from '@/views/dynasty';
import { ScrollArea } from '../ui/scroll-area';

interface LeagueStandingProps {
    userTeam: Team;
    leagueStandings: LeagueStandings[];
}

export function LeagueStanding({ userTeam, leagueStandings }: LeagueStandingProps) {
    return (
        <Card className="lg:col-span-4">
            <CardHeader className="pb-2">
                <CardTitle className="flex items-center">
                    <div className="flex items-center gap-2">League Standings</div>
                </CardTitle>
            </CardHeader>
            <CardContent className="p-0">
                <div className="px-6 pt-4">
                    <ScrollArea className="flex h-[400px]">
                        <Table className="w-full">
                            <TableHeader>
                                <TableRow>
                                    <TableHead className="w-[50px]">Pos</TableHead>
                                    <TableHead>Team</TableHead>
                                    <TableHead className="text-right">Games Played</TableHead>
                                    <TableHead className="text-right">Goals For</TableHead>
                                    <TableHead className="text-right">Goals Against</TableHead>
                                    <TableHead className="text-right">Points</TableHead>
                                    <TableHead className="text-right">Record</TableHead>
                                </TableRow>
                            </TableHeader>
                            <TableBody>
                                {leagueStandings
                                    .sort((a, b) => b.points - a.points)
                                    .map((team) => (
                                        <TableRow
                                            key={team.position}
                                            className={team.team.id === userTeam.id ? 'bg-muted font-medium' : ''}>
                                            <TableCell>{team.position}</TableCell>
                                            <TableCell>
                                                {team.team.id === userTeam.id ? (
                                                    <div className="flex items-center">{team.team.name}</div>
                                                ) : (
                                                    team.team.name
                                                )}
                                            </TableCell>
                                            <TableCell className="text-right">{team.gamePlayed}</TableCell>
                                            <TableCell className="text-right">{team.goalsFor}</TableCell>
                                            <TableCell className="text-right">{team.goalsAgainst}</TableCell>
                                            <TableCell className="text-right font-medium">{team.points}</TableCell>
                                            <TableCell className="text-right">
                                                {team.wins}-{team.losses}
                                            </TableCell>
                                        </TableRow>
                                    ))}
                            </TableBody>
                        </Table>
                    </ScrollArea>
                </div>
            </CardContent>
        </Card>
    );
}
