import React from 'react';
import { Translate } from 'react-jhipster';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/calendario">
        <Translate contentKey="global.menu.entities.calendario" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/facultad">
        <Translate contentKey="global.menu.entities.facultad" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/decano">
        <Translate contentKey="global.menu.entities.decano" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/vice-decano">
        <Translate contentKey="global.menu.entities.viceDecano" />
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
