import { Button } from '@/components/ui/button';
import {
    Dialog,
    DialogContent,
    DialogDescription,
    DialogHeader,
    DialogTitle,
    DialogTrigger,
} from '@/components/ui/dialog';
import Dynasty from '@/generated/com/hockeymanager/application/dynasties/models/Dynasty';
import { DynastiesService } from '@/generated/endpoints';
import { useEffect, useState } from 'react';
import DynastyTable from './dynasty-table';

interface LoadingDynastyDialog {
    setDynastyId: (dynastyId: string) => void;
}

export default function LoadingDynastyDialog({ setDynastyId }: LoadingDynastyDialog) {
    const [loading, setLoading] = useState<boolean>(false);
    const [dynasties, setDynasties] = useState<Dynasty[]>([]);

    useEffect(() => {
        const fetchDynasties = async () => {
            setLoading(true);
            const response = await DynastiesService.findAll();
            setDynasties(response!);
            setLoading(false);
        };
        fetchDynasties();
    }, []);
    return (
        <Dialog>
            <DialogTrigger asChild>
                <Button>Load Dynasty</Button>
            </DialogTrigger>
            <DialogContent className="sm:max-w-[425px]">
                <DialogHeader>
                    <DialogTitle>Load a dynasty</DialogTitle>
                    <DialogDescription>
                        Continue your journey by loading an existing dynasty. Select a dynasty from the list below to
                        load it.
                    </DialogDescription>
                </DialogHeader>
                {loading ? (
                    <div className="m-auto">Loading...</div>
                ) : dynasties.length > 0 ? (
                    <div className="grid gap-4 py-4">
                        <DynastyTable
                            dynasties={dynasties}
                            setSelectedDynastyId={setDynastyId}
                            setDynasties={setDynasties}
                        />
                    </div>
                ) : (
                    <div>No dynasties found.</div>
                )}
            </DialogContent>
        </Dialog>
    );
}
