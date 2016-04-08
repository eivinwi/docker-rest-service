"use strict";
var browser_1 = require('angular2/platform/browser');
var http_1 = require("angular2/http");
var router_1 = require('angular2/router');
var status_component_1 = require('../views/status.component');
browser_1.bootstrap(status_component_1.StatusComponent, [
    router_1.ROUTER_PROVIDERS,
    http_1.HTTP_PROVIDERS
]);
//# sourceMappingURL=main.js.map