export interface IDecano {
  id?: number;
  nombre?: string | null;
  email?: string | null;
  oficina?: string | null;
  nameFacultad?: string | null;
}

export const defaultValue: Readonly<IDecano> = {};
