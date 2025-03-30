import { Button } from '@/components/ui/button';
import { Table, TableBody, TableCaption, TableCell, TableHead, TableHeader, TableRow } from '@/components/ui/table';
import Dynasty from '@/generated/com/hockeymanager/application/dynasties/models/Dynasty';
import { DynastiesService } from '@/generated/endpoints';
import { X } from 'lucide-react';
import {
    AlertDialog,
    AlertDialogAction,
    AlertDialogCancel,
    AlertDialogContent,
    AlertDialogDescription,
    AlertDialogFooter,
    AlertDialogHeader,
    AlertDialogTitle,
    AlertDialogTrigger,
} from '../ui/alert-dialog';

interface DynastyTableProps {
    dynasties: Dynasty[];
    setDynasties: React.Dispatch<React.SetStateAction<Dynasty[]>>;
    setSelectedDynastyId: (teamId: string) => void;
}

export default function DynastyTable({ dynasties, setDynasties, setSelectedDynastyId }: DynastyTableProps) {
    const handleDelete = (dynastyId: string) => {
        DynastiesService.deleteById(dynastyId).then(() => {
            setDynasties(dynasties.filter((d) => d.id !== dynastyId));
        });
    };

    return (
        <Table>
            <TableCaption>A list of your current dynasties.</TableCaption>
            <TableHeader>
                <TableRow>
                    <TableHead className="w-[100px]">Name</TableHead>
                    <TableHead>Team</TableHead>
                    <TableHead></TableHead>
                </TableRow>
            </TableHeader>
            <TableBody>
                {dynasties.map((dynasty) => (
                    <TableRow key={dynasty.id} onClick={() => setSelectedDynastyId(dynasty.id!)}>
                        <TableCell className="font-medium">{dynasty.name}</TableCell>
                        <TableCell>{dynasty.team?.name}</TableCell>
                        <TableCell>
                            <AlertDialog>
                                <AlertDialogTrigger asChild>
                                    <Button
                                        variant="ghost"
                                        className="h-8 w-8 p-0"
                                        onClick={(e) => e.stopPropagation()}>
                                        <X />
                                    </Button>
                                </AlertDialogTrigger>
                                <AlertDialogContent>
                                    <AlertDialogHeader>
                                        <AlertDialogTitle>Are you sure ?</AlertDialogTitle>
                                        <AlertDialogDescription>
                                            This action cannot be undone. This will permanently delete your dynasty.
                                        </AlertDialogDescription>
                                    </AlertDialogHeader>
                                    <AlertDialogFooter>
                                        <AlertDialogCancel onClick={(e) => e.stopPropagation()}>
                                            Cancel
                                        </AlertDialogCancel>
                                        <AlertDialogAction
                                            onClick={(e) => {
                                                e.stopPropagation();
                                                handleDelete(dynasty.id!);
                                            }}>
                                            Continue
                                        </AlertDialogAction>
                                    </AlertDialogFooter>
                                </AlertDialogContent>
                            </AlertDialog>
                        </TableCell>
                    </TableRow>
                ))}
            </TableBody>
        </Table>
    );
}
