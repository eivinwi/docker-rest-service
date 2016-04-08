"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var core_1 = require('angular2/core');
var http_1 = require("angular2/http");
var StatusComponent = (function () {
    function StatusComponent(url) {
        //this.url = url;
        this.url = "http://localhost:8080";
    }
    StatusComponent.prototype.ngOnInit = function () {
        this.getTeams();
    };
    StatusComponent.prototype.getTeams = function () {
        this.headers = new http_1.Headers();
        this.headers.append("Content-Type", 'application/json');
        this.requestOptions = new http_1.RequestOptions({
            method: http_1.RequestMethod.Get,
            url: this.url + "/teams",
            headers: this.headers //,body: JSON.stringify(data)
        });
        return this.http.request(new http_1.Request(this.requestOptions))
            .map(function (res) {
            if (res) {
                return [{ status: res.status, json: res.json() }];
            }
        });
    };
    StatusComponent = __decorate([
        core_1.Component({
            selector: 'status',
            template: '<div>Hey</div>'
        }),
        core_1.View({
            template: '<div>View</div>'
        })
    ], StatusComponent);
    return StatusComponent;
}());
exports.StatusComponent = StatusComponent;
//# sourceMappingURL=status.component.js.map