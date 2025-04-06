import { Button } from '@/components/ui/button';
import {
    Dialog,
    DialogContent,
    DialogDescription,
    DialogFooter,
    DialogHeader,
    DialogTitle,
    DialogTrigger,
} from '@/components/ui/dialog';
import { Input } from '@/components/ui/input';
import { Label } from '@/components/ui/label';
import Goalie from '@/generated/com/hockeymanager/application/players/models/Goalie';
import Player from '@/generated/com/hockeymanager/application/players/models/Player';
import Team from '@/generated/com/hockeymanager/application/teams/models/Team';
import { DynastiesService, PatchesService } from '@/generated/endpoints';
import { useState } from 'react';
import TeamCombobox from './team-combobox';

interface CreatingDynastyDialogProps {
    setDynastyId: (dynastyId: string) => void;
}

export default function CreatingDynastyDialog({ setDynastyId }: CreatingDynastyDialogProps) {
    const [dynastyName, setDynastyName] = useState<string>('');
    const [fileError, setFileError] = useState<string>('');
    const [selectedDirectory, setSelectedDirectory] = useState<string>('');
    const [importing, setImporting] = useState<boolean>(false);
    const [teams, setTeams] = useState<Team[]>([]);
    const [players, setPlayers] = useState<Player[]>([]);
    const [goalies, setGoalies] = useState<Goalie[]>([]);
    const [selectedTeamId, setSelectedTeamId] = useState<string>('');
    const [isCreating, setIsCreating] = useState<boolean>(false);
    const [error, setError] = useState<string>('');

    const handleImport = async () => {
        try {
            setFileError('');
            setTeams([]);
            setPlayers([]);
            setGoalies([]);
            setSelectedTeamId('');
            setImporting(true);

            const response = await PatchesService.importPatch({
                folderPath: selectedDirectory,
            });

            if (response) {
                console.log('Import response:', response);
                setTeams(response.teams);
                setPlayers(response.players);
                setGoalies(response.goalies);
            } else {
                setFileError('No data found in the selected directory.');
            }
        } catch {
            setFileError('An error occurred while importing the patch. Please check the directory and try again.');
        } finally {
            setImporting(false);
        }
    };

    const handleSubmit = async () => {
        try {
            setError('');
            setIsCreating(true);
            const createdDynasty = await DynastiesService.create({
                name: dynastyName,
                teamId: selectedTeamId,
                teams: teams,
                players: players,
                goalies: goalies,
            });

            if (createdDynasty !== undefined && createdDynasty !== '') {
                setDynastyId(createdDynasty);
            }
        } catch (error) {
            setError(error as string);
        }

        setIsCreating(false);
    };

    return (
        <Dialog>
            <DialogTrigger asChild>
                <Button>New Dynasty</Button>
            </DialogTrigger>
            <DialogContent className="sm:max-w-[425px]">
                <DialogHeader>
                    <DialogTitle>Create a new dynasty</DialogTitle>
                    <DialogDescription>
                        Create a new dynasty to start your journey. You can always create more dynasties later.
                    </DialogDescription>
                </DialogHeader>
                <div className="grid gap-4 py-4">
                    <div className="grid grid-cols-4 items-center gap-4">
                        <Label htmlFor="name" className="text-right">
                            Name
                        </Label>
                        <Input
                            id="name"
                            value={dynastyName}
                            onChange={(e) => setDynastyName(e.currentTarget.value)}
                            className="col-span-3"
                        />
                    </div>
                    <div className="grid grid-cols-4 items-center gap-4">
                        <Label htmlFor="name">Patch directory</Label>
                        <Input
                            id="name"
                            value={selectedDirectory}
                            onChange={(e) => setSelectedDirectory(e.currentTarget.value)}
                            className="col-span-3"
                        />
                    </div>
                    <div className="grid grid-cols-4 items-center gap-4">
                        <Button
                            disabled={importing}
                            className="col-span-4"
                            type="button"
                            variant="outline"
                            onClick={handleImport}>
                            {importing ? 'Currently importing...' : 'Import'}
                        </Button>

                        {fileError && <span className="text-xs text-destructive col-span-4">{fileError}</span>}
                    </div>
                    {teams.length > 0 && (
                        <TeamCombobox
                            teams={teams}
                            selectedTeamId={selectedTeamId}
                            setSelectedTeamId={setSelectedTeamId}
                        />
                    )}
                    {error && <span className="text-xs text-destructive col-span-4">{error}</span>}
                </div>
                <DialogFooter>
                    <Button
                        disabled={
                            selectedTeamId == '' ||
                            dynastyName == '' ||
                            importing ||
                            selectedDirectory == '' ||
                            error != '' ||
                            fileError != '' ||
                            isCreating
                        }
                        onClick={handleSubmit}>
                        {isCreating ? 'Creating...' : 'Start dynasty'}
                    </Button>
                </DialogFooter>
            </DialogContent>
        </Dialog>
    );
}
