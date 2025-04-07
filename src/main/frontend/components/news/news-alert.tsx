import { Button } from '@/components/ui/button';
import { X } from 'lucide-react';

interface NewsAlertProps {
    title: string;
    description: string;
    onDismiss: () => void;
}

export function NewsAlert({ title, description, onDismiss }: NewsAlertProps) {
    return (
        <div className="flex items-start justify-between space-x-4 pr-2">
            <div className="space-y-1">
                <p className="text-sm font-medium leading-none">{title}</p>
                <p className="text-sm text-muted-foreground">{description}</p>
            </div>
            <Button
                variant="ghost"
                size="icon"
                className="h-6 w-6 flex-shrink-0"
                aria-label={`Dismiss news: ${title}`}
                onClick={onDismiss}>
                <X className="h-4 w-4" />
            </Button>
        </div>
    );
}
