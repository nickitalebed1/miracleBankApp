import React from "react";
import {connect} from "react-redux"

import {Alert} from 'react-bootstrap';
import {Button} from 'react-bootstrap';

import {doLogout} from "../actions/loginActions";

import { ResponsiveContainer, LineChart, Line, XAxis, YAxis, ReferenceLine,
    ReferenceDot, Tooltip, CartesianGrid, Legend, Brush } from 'recharts';

@connect((store) => {
    return {

        userData: store.login.userData,
    };
})

class AfterLogin extends React.Component {

    doLogout() {
        this.props.dispatch(doLogout())
    }

    render() {
        const {userData} = this.props;
        const data01 = [
            { day: '05-01', weather: 'sunny' },
            { day: '05-02', weather: 'sunny' },
            { day: '05-03', weather: 'cloudy' },
            { day: '05-04', weather: 'rain' },
            { day: '05-05', weather: 'rain' },
            { day: '05-06', weather: 'cloudy' },
            { day: '05-07', weather: 'cloudy' },
            { day: '05-08', weather: 'sunny' },
            { day: '05-09', weather: 'sunny' },
        ];

        const btn = {
            margin: '30px',
        };

        let html;
        if (userData) {
            html = (
                <div>
                    <Alert bsStyle="info">
                        You are logged as {userData.username}. If you want to logout click on the button below.
                    </Alert>
                    <LineChart
                        width={400} height={400} data={data01}
                        margin={{ top: 20, right: 20, bottom: 20, left: 20 }}>
                        <XAxis dataKey="day" />
                        <YAxis type="category" />
                        <Tooltip />
                        <Line type="stepAfter" dataKey="weather" stroke="#ff7300" />
                    </LineChart>
                    <Button bsStyle="primary" onClick={this.doLogout.bind(this)} style={btn}>
                        Logout
                    </Button>
                </div>
            )
        } else {
            html = ( <div></div>)
        }
        return ( <div>{ html }</div>)
    }
}


export default AfterLogin;