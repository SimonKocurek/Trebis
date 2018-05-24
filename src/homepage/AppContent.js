import * as React from 'react';
import { View, ScrollView } from 'react-native';
import { FAB, Paper, Button } from 'react-native-paper';
import { Template } from './Template';
import { Store } from '../Store';

export class AppContent extends React.Component {

    render() {
        return (
            <View style={{ flex: 1 }}>
                <ScrollView contentContainerStyle={{ flexGrow: 1, padding: 20 }}>
                    <Template />
                </ScrollView>

                {
                     Store.kebabOpened && (
                        <Paper style={{ position: 'absolute', top: 0, right: 15, elevation: 5 }}>
                            <Button
                                onPress={() => this.props.navigation.navigate('Settings')}>
                                Settings
                            </Button>
                        </Paper>
                    )
                }


                <FAB
                    style={{ position: 'absolute', right: 20, bottom: 20 }}
                    icon="add"
                    onPress={() => { this.props.navigation.navigate('TemplateEditor') }} />
            </View>
        );
    }
}