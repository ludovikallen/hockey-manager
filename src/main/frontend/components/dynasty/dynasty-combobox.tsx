import { Button } from '@/components/ui/button';
import { Command, CommandEmpty, CommandGroup, CommandItem, CommandList } from '@/components/ui/command';
import { Popover, PopoverContent, PopoverTrigger } from '@/components/ui/popover';
import { ScrollArea } from '@/components/ui/scroll-area';
import Dynasty from '@/generated/com/hockeymanager/application/dynasties/models/Dynasty';
import { ChevronsUpDown } from 'lucide-react';
import { useState } from 'react';

interface DynastyComboboxProps {
    dynasties: Dynasty[];
    setSelectedDynastyId: (teamId: string) => void;
}

export default function DynastyCombobox({ dynasties, setSelectedDynastyId }: DynastyComboboxProps) {
    const [open, setOpen] = useState<boolean>(false);

    return (
        <Popover modal={true} open={open} onOpenChange={setOpen}>
            <PopoverTrigger asChild>
                <Button variant="outline" role="combobox" aria-expanded={open} className="w-full justify-between">
                    Select your dynasty...
                    <ChevronsUpDown className="ml-2 h-4 w-4 shrink-0 opacity-50" />
                </Button>
            </PopoverTrigger>
            <PopoverContent className="w-full p-0">
                <Command>
                    <CommandList>
                        <CommandEmpty>No team found.</CommandEmpty>
                        <CommandGroup>
                            <ScrollArea className="h-20 w-90 rounded-md border">
                                {dynasties
                                    .sort((x, y) => (x.name! > y.name! ? 1 : -1))
                                    .map((dynasty) => (
                                        <CommandItem
                                            key={dynasty.id}
                                            value={dynasty.id}
                                            onSelect={(currentValue) => {
                                                setSelectedDynastyId(currentValue);
                                                setOpen(false);
                                            }}>
                                            {dynasty.name}
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
