export class Config {

    accessToken:string;
    baseUrl:string;
    pageSize: number;
    tax:number;

    constructor(){
        this.accessToken = "278f8106299f3cea0a70d7ac69b0781ada4c8414";
        this.baseUrl = "http://localhost:8080/cms/rest/api";
        this.pageSize = 20;
        this.tax = 0.1;
    }

}