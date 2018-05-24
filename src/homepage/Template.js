import * as React from 'react';
import {
    Button,
    Card,
    CardActions,
    CardContent,
    CardCover,
    Title,
    Paragraph
} from 'react-native-paper';

export class Template extends React.Component {
    render() {
        return (
            <Card>
                <CardContent>
                    <Title>Card title</Title>
                    <Paragraph>Card content</Paragraph>
                </CardContent>
                <CardCover source={{ uri: 'https://picsum.photos/700' }} />
                <CardActions>
                    <Button>Cancel</Button>
                    <Button>Ok</Button>
                </CardActions>
            </Card>
        );
    }
}