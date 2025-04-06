import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { ScrollArea } from '@/components/ui/scroll-area';
import { Separator } from '@/components/ui/separator';
import News from '@/generated/com/hockeymanager/application/news/models/News';
import { NewsService } from '@/generated/endpoints';
import { NewsAlert } from './news-alert';

interface NewsBoxProps {
    news: News[];
    setNews: React.Dispatch<React.SetStateAction<News[]>>;
}

export function NewsBox({ news, setNews }: NewsBoxProps) {
    const handleDismiss = (newsId: string) => {
        console.log('clicked');
        NewsService.dismissById(newsId).then(() => {
            setNews(news.filter((d) => d.id !== newsId));
        });
    };

    return (
        <Card className="w-full h-80">
            <CardHeader>
                <CardTitle>Alerts & News</CardTitle>
            </CardHeader>
            <CardContent className="p-0">
                <div className="px-6">
                    <ScrollArea className="flex flex-col w-full h-56">
                        {news.length == 0 ? (
                            <div className="text-sm">You're all catch up on your news.</div>
                        ) : (
                            news.map((n, i) => (
                                <div key={n.id} className="flex flex-col space-y-2 pb-2">
                                    <NewsAlert
                                        title={n.title}
                                        description={n.description}
                                        onDismiss={() => handleDismiss(n.id)}
                                    />
                                    {i < news.length - 1 && <Separator />}
                                </div>
                            ))
                        )}
                    </ScrollArea>
                </div>
            </CardContent>
        </Card>
    );
}
