import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Facultad from './facultad';
import FacultadDetail from './facultad-detail';
import FacultadUpdate from './facultad-update';
import FacultadDeleteDialog from './facultad-delete-dialog';

const FacultadRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Facultad />} />
    <Route path="new" element={<FacultadUpdate />} />
    <Route path=":id">
      <Route index element={<FacultadDetail />} />
      <Route path="edit" element={<FacultadUpdate />} />
      <Route path="delete" element={<FacultadDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default FacultadRoutes;
