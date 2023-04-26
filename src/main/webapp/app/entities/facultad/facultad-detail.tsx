import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './facultad.reducer';

export const FacultadDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const facultadEntity = useAppSelector(state => state.facultad.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="facultadDetailsHeading">
          <Translate contentKey="lab3FabricaApp.facultad.detail.title">Facultad</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{facultadEntity.id}</dd>
          <dt>
            <span id="nombre">
              <Translate contentKey="lab3FabricaApp.facultad.nombre">Nombre</Translate>
            </span>
          </dt>
          <dd>{facultadEntity.nombre}</dd>
          <dt>
            <Translate contentKey="lab3FabricaApp.facultad.decano">Decano</Translate>
          </dt>
          <dd>{facultadEntity.decano ? facultadEntity.decano.id : ''}</dd>
          <dt>
            <Translate contentKey="lab3FabricaApp.facultad.viceDecano">Vice Decano</Translate>
          </dt>
          <dd>{facultadEntity.viceDecano ? facultadEntity.viceDecano.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/facultad" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/facultad/${facultadEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default FacultadDetail;
