import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Calendario from './calendario';
import CalendarioDetail from './calendario-detail';
import CalendarioUpdate from './calendario-update';
import CalendarioDeleteDialog from './calendario-delete-dialog';

const CalendarioRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Calendario />} />
    <Route path="new" element={<CalendarioUpdate />} />
    <Route path=":id">
      <Route index element={<CalendarioDetail />} />
      <Route path="edit" element={<CalendarioUpdate />} />
      <Route path="delete" element={<CalendarioDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default CalendarioRoutes;
