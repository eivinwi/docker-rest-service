import {Component} from 'angular2/core';
import {RouteConfig, ROUTER_DIRECTIVES} from 'angular2/router';
import {StatusComponent} from "./status.component";

@Component({
    selector: 'my-app',
    template: '<div>Dis my app, yo</div>'
})
@RouteConfig([
    { path: '/status', name: 'Status', component: StatusComponent, useAsDefault: true }
])
