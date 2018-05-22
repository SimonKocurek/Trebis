import * as React from 'react';
import { View, ScrollView } from 'react-native';
import { FAB } from 'react-native-paper';
import Template from './Template';

export default class AppContent extends React.Component {

    render() {
        return (
            <View>
                <FAB
                    style={{ position: 'absolute', right: 10}}
                    icon="add"
                    onPress={() => {this.props.navigation.navigate('TemplateEditor')}} />

                <ScrollView style={{ padding: 20 }}>
                    <Template />
                    <Template />
                    <Template />
                    <Template />
                    <Template />
                </ScrollView>
            </View>

        );
    }
}