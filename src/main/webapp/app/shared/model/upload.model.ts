export interface IUpload {
  id?: string;
  fileContentType?: string;
  file?: any;
  imageContentType?: string;
  image?: any;
}

export class Upload implements IUpload {
  constructor(
    public id?: string,
    public fileContentType?: string,
    public file?: any,
    public imageContentType?: string,
    public image?: any
  ) {}
}
