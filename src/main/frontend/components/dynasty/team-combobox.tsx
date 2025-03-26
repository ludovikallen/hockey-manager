import { Button } from '@/components/ui/button';
import { Command, CommandEmpty, CommandGroup, CommandItem, CommandList } from '@/components/ui/command';
import { Popover, PopoverContent, PopoverTrigger } from '@/components/ui/popover';
import { ScrollArea } from '@/components/ui/scroll-area';
import Team from '@/generated/com/hockeymanager/application/teams/models/Team';
import { cn } from '@/lib/utils';
import { Check, ChevronsUpDown } from 'lucide-react';
import { useState } from 'react';

interface TeamComboboxProps {
    teams: Team[];
    selectedTeamId: string;
    setSelectedTeamId: (teamId: string) => void;
}

export default function TeamCombobox({ teams, selectedTeamId, setSelectedTeamId }: TeamComboboxProps) {
    const [open, setOpen] = useState<boolean>(false);

    return (
        <Popover modal={true} open={open} onOpenChange={setOpen}>
            <PopoverTrigger asChild>
                <Button variant="outline" role="combobox" aria-expanded={open} className="w-full justify-between">
                    {selectedTeamId ? teams.find((team) => team.id === selectedTeamId)?.name : 'Select your team...'}
                    <ChevronsUpDown className="ml-2 h-4 w-4 shrink-0 opacity-50" />
                </Button>
            </PopoverTrigger>
            <PopoverContent className="w-full p-0">
                <Command>
                    <CommandList>
                        <CommandEmpty>No team found.</CommandEmpty>
                        <CommandGroup>
                            <ScrollArea className="h-72 w-90 rounded-md border">
                                {teams
                                    .sort((x, y) => (x.name! > y.name! ? 1 : -1))
                                    .map((team) => (
                                        <CommandItem
                                            key={team.id}
                                            value={team.id}
                                            onSelect={(currentValue) => {
                                                setSelectedTeamId(currentValue);
                                                setOpen(false);
                                            }}>
                                            <Check
                                                className={cn(
                                                    'mr-2 h-4 w-4',
                                                    selectedTeamId === team.id ? 'opacity-100' : 'opacity-0',
                                                )}
                                            />
                                            {team.name}
                                        </CommandItem>
                                    ))}
                            </ScrollArea>
                        </CommandGroup>
                    </CommandList>
                </Command>
            </PopoverContent>
        </Popover>
    );
}
