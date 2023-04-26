import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './vice-decano.reducer';

export const ViceDecanoDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const viceDecanoEntity = useAppSelector(state => state.viceDecano.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="viceDecanoDetailsHeading">
          <Translate contentKey="lab3FabricaApp.viceDecano.detail.title">ViceDecano</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{viceDecanoEntity.id}</dd>
          <dt>
            <span id="nombre">
              <Translate contentKey="lab3FabricaApp.viceDecano.nombre">Nombre</Translate>
            </span>
          </dt>
          <dd>{viceDecanoEntity.nombre}</dd>
          <dt>
            <span id="email">
              <Translate contentKey="lab3FabricaApp.viceDecano.email">Email</Translate>
            </span>
          </dt>
          <dd>{viceDecanoEntity.email}</dd>
          <dt>
            <span id="oficina">
              <Translate contentKey="lab3FabricaApp.viceDecano.oficina">Oficina</Translate>
            </span>
          </dt>
          <dd>{viceDecanoEntity.oficina}</dd>
          <dt>
            <span id="nameFacultad">
              <Translate contentKey="lab3FabricaApp.viceDecano.nameFacultad">Name Facultad</Translate>
            </span>
          </dt>
          <dd>{viceDecanoEntity.nameFacultad}</dd>
        </dl>
        <Button tag={Link} to="/vice-decano" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/vice-decano/${viceDecanoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ViceDecanoDetail;
