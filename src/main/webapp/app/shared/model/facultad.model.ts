import { IDecano } from 'app/shared/model/decano.model';
import { IViceDecano } from 'app/shared/model/vice-decano.model';

export interface IFacultad {
  id?: number;
  nombre?: string | null;
  decano?: IDecano | null;
  viceDecano?: IViceDecano | null;
}

export const defaultValue: Readonly<IFacultad> = {};
