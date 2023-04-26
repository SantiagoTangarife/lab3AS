import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IViceDecano } from 'app/shared/model/vice-decano.model';
import { getEntity, updateEntity, createEntity, reset } from './vice-decano.reducer';

export const ViceDecanoUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const viceDecanoEntity = useAppSelector(state => state.viceDecano.entity);
  const loading = useAppSelector(state => state.viceDecano.loading);
  const updating = useAppSelector(state => state.viceDecano.updating);
  const updateSuccess = useAppSelector(state => state.viceDecano.updateSuccess);

  const handleClose = () => {
    navigate('/vice-decano');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...viceDecanoEntity,
      ...values,
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...viceDecanoEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="lab3FabricaApp.viceDecano.home.createOrEditLabel" data-cy="ViceDecanoCreateUpdateHeading">
            <Translate contentKey="lab3FabricaApp.viceDecano.home.createOrEditLabel">Create or edit a ViceDecano</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="vice-decano-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('lab3FabricaApp.viceDecano.nombre')}
                id="vice-decano-nombre"
                name="nombre"
                data-cy="nombre"
                type="text"
              />
              <ValidatedField
                label={translate('lab3FabricaApp.viceDecano.email')}
                id="vice-decano-email"
                name="email"
                data-cy="email"
                type="text"
                validate={{}}
              />
              <ValidatedField
                label={translate('lab3FabricaApp.viceDecano.oficina')}
                id="vice-decano-oficina"
                name="oficina"
                data-cy="oficina"
                type="text"
              />
              <ValidatedField
                label={translate('lab3FabricaApp.viceDecano.nameFacultad')}
                id="vice-decano-nameFacultad"
                name="nameFacultad"
                data-cy="nameFacultad"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/vice-decano" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default ViceDecanoUpdate;
