import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Calendario from './calendario';
import Facultad from './facultad';
import Decano from './decano';
import ViceDecano from './vice-decano';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="calendario/*" element={<Calendario />} />
        <Route path="facultad/*" element={<Facultad />} />
        <Route path="decano/*" element={<Decano />} />
        <Route path="vice-decano/*" element={<ViceDecano />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
