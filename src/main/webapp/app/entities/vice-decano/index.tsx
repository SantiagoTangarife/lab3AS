import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import ViceDecano from './vice-decano';
import ViceDecanoDetail from './vice-decano-detail';
import ViceDecanoUpdate from './vice-decano-update';
import ViceDecanoDeleteDialog from './vice-decano-delete-dialog';

const ViceDecanoRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<ViceDecano />} />
    <Route path="new" element={<ViceDecanoUpdate />} />
    <Route path=":id">
      <Route index element={<ViceDecanoDetail />} />
      <Route path="edit" element={<ViceDecanoUpdate />} />
      <Route path="delete" element={<ViceDecanoDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ViceDecanoRoutes;
