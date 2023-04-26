import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IDecano } from 'app/shared/model/decano.model';
import { getEntity, updateEntity, createEntity, reset } from './decano.reducer';

export const DecanoUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const decanoEntity = useAppSelector(state => state.decano.entity);
  const loading = useAppSelector(state => state.decano.loading);
  const updating = useAppSelector(state => state.decano.updating);
  const updateSuccess = useAppSelector(state => state.decano.updateSuccess);

  const handleClose = () => {
    navigate('/decano');
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
      ...decanoEntity,
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
          ...decanoEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="lab3FabricaApp.decano.home.createOrEditLabel" data-cy="DecanoCreateUpdateHeading">
            <Translate contentKey="lab3FabricaApp.decano.home.createOrEditLabel">Create or edit a Decano</Translate>
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
                  id="decano-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('lab3FabricaApp.decano.nombre')}
                id="decano-nombre"
                name="nombre"
                data-cy="nombre"
                type="text"
              />
              <ValidatedField
                label={translate('lab3FabricaApp.decano.email')}
                id="decano-email"
                name="email"
                data-cy="email"
                type="text"
                validate={{}}
              />
              <ValidatedField
                label={translate('lab3FabricaApp.decano.oficina')}
                id="decano-oficina"
                name="oficina"
                data-cy="oficina"
                type="text"
              />
              <ValidatedField
                label={translate('lab3FabricaApp.decano.nameFacultad')}
                id="decano-nameFacultad"
                name="nameFacultad"
                data-cy="nameFacultad"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/decano" replace color="info">
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

export default DecanoUpdate;
