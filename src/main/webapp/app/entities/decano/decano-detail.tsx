import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './decano.reducer';

export const DecanoDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const decanoEntity = useAppSelector(state => state.decano.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="decanoDetailsHeading">
          <Translate contentKey="lab3FabricaApp.decano.detail.title">Decano</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{decanoEntity.id}</dd>
          <dt>
            <span id="nombre">
              <Translate contentKey="lab3FabricaApp.decano.nombre">Nombre</Translate>
            </span>
          </dt>
          <dd>{decanoEntity.nombre}</dd>
          <dt>
            <span id="email">
              <Translate contentKey="lab3FabricaApp.decano.email">Email</Translate>
            </span>
          </dt>
          <dd>{decanoEntity.email}</dd>
          <dt>
            <span id="oficina">
              <Translate contentKey="lab3FabricaApp.decano.oficina">Oficina</Translate>
            </span>
          </dt>
          <dd>{decanoEntity.oficina}</dd>
          <dt>
            <span id="nameFacultad">
              <Translate contentKey="lab3FabricaApp.decano.nameFacultad">Name Facultad</Translate>
            </span>
          </dt>
          <dd>{decanoEntity.nameFacultad}</dd>
        </dl>
        <Button tag={Link} to="/decano" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/decano/${decanoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default DecanoDetail;
