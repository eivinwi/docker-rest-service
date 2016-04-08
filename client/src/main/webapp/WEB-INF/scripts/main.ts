import {bootstrap} from 'angular2/platform/browser';
import {HTTP_PROVIDERS} from "angular2/http";
import {ROUTER_PROVIDERS} from 'angular2/router';
import {StatusComponent} from '../views/status.component';

bootstrap(StatusComponent, [
    ROUTER_PROVIDERS,
    HTTP_PROVIDERS
]);
