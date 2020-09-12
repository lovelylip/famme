import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IUpload } from 'app/shared/model/upload.model';

type EntityResponseType = HttpResponse<IUpload>;
type EntityArrayResponseType = HttpResponse<IUpload[]>;

@Injectable({ providedIn: 'root' })
export class UploadService {
  public resourceUrl = SERVER_API_URL + 'api/uploads';

  constructor(protected http: HttpClient) {}

  create(upload: IUpload): Observable<EntityResponseType> {
    return this.http.post<IUpload>(this.resourceUrl, upload, { observe: 'response' });
  }

  update(upload: IUpload): Observable<EntityResponseType> {
    return this.http.put<IUpload>(this.resourceUrl, upload, { observe: 'response' });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IUpload>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IUpload[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
