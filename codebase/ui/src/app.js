import {MainWrapper} from './components/mainwrapper/MainWrapper';
import {SeminarList} from './components/overview/SeminarList';
import {SeminarDetail} from './components/detail/SeminarDetail';
import {PrintableInvoice} from './components/detail/invoice/PrintableInvoice';
import {Profile} from './components/profile/Profile';
import {BookingHistory} from './components/profile/BookingHistory';
import {CreateSeminar} from './components/create/CreateSeminar';
import {NotFound} from './components/general/NotFound';
import {Error} from './components/general/Error';
import {Login} from './components/general/Login';
import {EmployeeList} from './components/divisionheadoverview/EmployeeList';
import React from 'react';
import ReactDOM from 'react-dom';
import 'whatwg-fetch'; //Github fetch
import {Router, Route, Link, IndexRedirect, hashHistory} from 'react-router'
import AuthService from './services/AuthService';
//material-ui
import injectTapEventPlugin from 'react-tap-event-plugin';
// Needed for onTouchTap
// http://stackoverflow.com/a/34015469/988941
injectTapEventPlugin();

require('./App.scss');

class App extends React.Component {
    constructor() {
        super();
    }
}

function redirectToLogin(nextState, replace) {
    if (!AuthService.isLoggedIn()) {
        replace({
            pathname: '/login',
            state: {nextPathname: nextState.location.pathname}
        });
    }
}

function checkIfFrontoffice(nextState, replace) {
    if(!AuthService.isFrontOffice()){
        replace({
            pathname: '/seminars',
            state: {nextPathname: nextState.location.pathname}
        });
    }
}

ReactDOM.render(
    <Router history={hashHistory}>
        <Route path="/login" component={Login}/>
        <Route path="/" component={MainWrapper} onEnter={redirectToLogin}>
            <IndexRedirect to="/seminars"/>
            <Route path="seminars" component={SeminarList}/>
            <Route path="seminars/:seminarId" component={SeminarDetail}/>
            <Route path="create" component={CreateSeminar} onEnter={checkIfFrontoffice}/>
            <Route path="profile" component={Profile}/>
            <Route path="bereichuebersicht" component={EmployeeList}/>
            <Route path="history" component={BookingHistory}/>
            <Route path="invoice/:seminarId" component={PrintableInvoice}/>
            <Route path="error" component={Error}/>
            <Route path="*" component={NotFound}/>
        </Route>
    </Router>
    , document.getElementById('content'));
