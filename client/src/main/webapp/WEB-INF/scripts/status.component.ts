import {Component, View, OnInit} from 'angular2/core';
import {Team} from '../scripts/teamdetails.component';
import {RouteConfig} from "angular2/router";
import {Headers, Request, Response, RequestOptions, RequestMethod} from "angular2/http";

@Component({
    selector: 'status',
    template: '<div>Hey</div>'
})
@View({
  template: '<div>View</div>'
})

export class StatusComponent implements OnInit {
    teams: Team[];
    headers: any;
    http: any;
    requestOptions: any;
    url: string;

    constructor(url: string) {
        //this.url = url;
        this.url = "http://localhost:8080";
    }

    ngOnInit() {
        this.getTeams();
    }

    getTeams() {
        this.headers = new Headers();
        this.headers.append("Content-Type", 'application/json');

        this.requestOptions = new RequestOptions({
            method: RequestMethod.Get,
            url: this.url + "/teams",
            headers: this.headers //,body: JSON.stringify(data)
        });

        return this.http.request(new Request(this.requestOptions))
            .map((res: Response) => {
                if(res) {
                    return [{ status: res.status, json: res.json()}]
                }
            });
    }
}