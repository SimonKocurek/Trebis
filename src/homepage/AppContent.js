import * as React from 'react';
import { View, ScrollView } from 'react-native';
import { FAB } from 'react-native-paper';
import Template from './Template';

export default class AppContent extends React.Component {

    render() {
        return (
            <View style={{ flex: 1 }}>
                <ScrollView contentContainerStyle={{ flexGrow: 1, padding: 20 }}>
                    <Template />
                    <Template />
                    <Template />
                    <Template />
                    <Template />
                </ScrollView>

                <FAB
                    style={{ position: 'absolute', right: 20, bottom: 20 }}
                    icon="add"
                    onPress={() => { this.props.navigation.navigate('TemplateEditor') }} />
            </View>
        );
    }
}