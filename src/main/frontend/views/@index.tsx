import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { Label } from '@/components/ui/label';
import { ViewConfig } from '@vaadin/hilla-file-router/types.js';
import { useSignal } from '@vaadin/hilla-react-signals';
import { HelloWorldService } from 'Frontend/generated/endpoints.js';
import { toast } from 'sonner';

export const config: ViewConfig = {
    menu: { order: 0, icon: 'line-awesome/svg/globe-solid.svg' },
    title: 'Hello World',
};

export default function HelloWorldView() {
    const name = useSignal('');

    return (
        <section className="flex flex-col gap-4 m-auto">
            <Label>Your name</Label>
            <Input
                placeholder="Ludovik"
                value={name.value}
                onInput={(e) => (name.value = e.currentTarget.value)}></Input>
            <Button
                onClick={async () => {
                    const serverResponse = await HelloWorldService.sayHello(name.value);
                    toast(serverResponse);
                }}>
                Say hello
            </Button>
        </section>
    );
}
