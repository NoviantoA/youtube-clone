import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {UploadVideoResponse} from "./upload-video/upload-video-response";

@Injectable({
  providedIn: 'root'
})
export class VideoService {

  constructor(private httpClient: HttpClient) { }

  uploadVideo(fileEntry: File) : Observable<UploadVideoResponse>{
    const formData = new FormData()
    // nama 'file' harus sama dengan @RequestParam di backend
    formData.append('file', fileEntry, fileEntry.name)
    // HTTP Post Call to upload video
    return this.httpClient.post<UploadVideoResponse>('http://localhost:8080/api/videos/', formData)
  }
}
