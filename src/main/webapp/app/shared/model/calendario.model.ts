import dayjs from 'dayjs';
import { IFacultad } from 'app/shared/model/facultad.model';

export interface ICalendario {
  id?: number;
  semestre?: string | null;
  publicacionOferta?: string | null;
  inicioMatriculas?: string | null;
  finMatriculas?: string | null;
  inicioAjustes?: string | null;
  finAjustes?: string | null;
  inicioClases?: string | null;
  finClases?: string | null;
  inicioExamenesFinales?: string | null;
  finExamenesFinales?: string | null;
  inicioValidaciones?: string | null;
  finValidaciones?: string | null;
  inicioHabilitaciones?: string | null;
  finHabilitaciones?: string | null;
  terminacionOficinal?: string | null;
  facultad?: IFacultad | null;
}

export const defaultValue: Readonly<ICalendario> = {};
