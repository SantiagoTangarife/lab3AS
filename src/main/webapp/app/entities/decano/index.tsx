import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Decano from './decano';
import DecanoDetail from './decano-detail';
import DecanoUpdate from './decano-update';
import DecanoDeleteDialog from './decano-delete-dialog';

const DecanoRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Decano />} />
    <Route path="new" element={<DecanoUpdate />} />
    <Route path=":id">
      <Route index element={<DecanoDetail />} />
      <Route path="edit" element={<DecanoUpdate />} />
      <Route path="delete" element={<DecanoDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default DecanoRoutes;
