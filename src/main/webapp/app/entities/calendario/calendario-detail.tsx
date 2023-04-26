import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './calendario.reducer';

export const CalendarioDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const calendarioEntity = useAppSelector(state => state.calendario.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="calendarioDetailsHeading">
          <Translate contentKey="lab3FabricaApp.calendario.detail.title">Calendario</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{calendarioEntity.id}</dd>
          <dt>
            <span id="semestre">
              <Translate contentKey="lab3FabricaApp.calendario.semestre">Semestre</Translate>
            </span>
          </dt>
          <dd>{calendarioEntity.semestre}</dd>
          <dt>
            <span id="publicacionOferta">
              <Translate contentKey="lab3FabricaApp.calendario.publicacionOferta">Publicacion Oferta</Translate>
            </span>
          </dt>
          <dd>
            {calendarioEntity.publicacionOferta ? (
              <TextFormat value={calendarioEntity.publicacionOferta} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="inicioMatriculas">
              <Translate contentKey="lab3FabricaApp.calendario.inicioMatriculas">Inicio Matriculas</Translate>
            </span>
          </dt>
          <dd>
            {calendarioEntity.inicioMatriculas ? (
              <TextFormat value={calendarioEntity.inicioMatriculas} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="finMatriculas">
              <Translate contentKey="lab3FabricaApp.calendario.finMatriculas">Fin Matriculas</Translate>
            </span>
          </dt>
          <dd>
            {calendarioEntity.finMatriculas ? (
              <TextFormat value={calendarioEntity.finMatriculas} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="inicioAjustes">
              <Translate contentKey="lab3FabricaApp.calendario.inicioAjustes">Inicio Ajustes</Translate>
            </span>
          </dt>
          <dd>
            {calendarioEntity.inicioAjustes ? (
              <TextFormat value={calendarioEntity.inicioAjustes} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="finAjustes">
              <Translate contentKey="lab3FabricaApp.calendario.finAjustes">Fin Ajustes</Translate>
            </span>
          </dt>
          <dd>
            {calendarioEntity.finAjustes ? <TextFormat value={calendarioEntity.finAjustes} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="inicioClases">
              <Translate contentKey="lab3FabricaApp.calendario.inicioClases">Inicio Clases</Translate>
            </span>
          </dt>
          <dd>
            {calendarioEntity.inicioClases ? (
              <TextFormat value={calendarioEntity.inicioClases} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="finClases">
              <Translate contentKey="lab3FabricaApp.calendario.finClases">Fin Clases</Translate>
            </span>
          </dt>
          <dd>
            {calendarioEntity.finClases ? <TextFormat value={calendarioEntity.finClases} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="inicioExamenesFinales">
              <Translate contentKey="lab3FabricaApp.calendario.inicioExamenesFinales">Inicio Examenes Finales</Translate>
            </span>
          </dt>
          <dd>
            {calendarioEntity.inicioExamenesFinales ? (
              <TextFormat value={calendarioEntity.inicioExamenesFinales} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="finExamenesFinales">
              <Translate contentKey="lab3FabricaApp.calendario.finExamenesFinales">Fin Examenes Finales</Translate>
            </span>
          </dt>
          <dd>
            {calendarioEntity.finExamenesFinales ? (
              <TextFormat value={calendarioEntity.finExamenesFinales} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="inicioValidaciones">
              <Translate contentKey="lab3FabricaApp.calendario.inicioValidaciones">Inicio Validaciones</Translate>
            </span>
          </dt>
          <dd>
            {calendarioEntity.inicioValidaciones ? (
              <TextFormat value={calendarioEntity.inicioValidaciones} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="finValidaciones">
              <Translate contentKey="lab3FabricaApp.calendario.finValidaciones">Fin Validaciones</Translate>
            </span>
          </dt>
          <dd>
            {calendarioEntity.finValidaciones ? (
              <TextFormat value={calendarioEntity.finValidaciones} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="inicioHabilitaciones">
              <Translate contentKey="lab3FabricaApp.calendario.inicioHabilitaciones">Inicio Habilitaciones</Translate>
            </span>
          </dt>
          <dd>
            {calendarioEntity.inicioHabilitaciones ? (
              <TextFormat value={calendarioEntity.inicioHabilitaciones} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="finHabilitaciones">
              <Translate contentKey="lab3FabricaApp.calendario.finHabilitaciones">Fin Habilitaciones</Translate>
            </span>
          </dt>
          <dd>
            {calendarioEntity.finHabilitaciones ? (
              <TextFormat value={calendarioEntity.finHabilitaciones} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="terminacionOficinal">
              <Translate contentKey="lab3FabricaApp.calendario.terminacionOficinal">Terminacion Oficinal</Translate>
            </span>
          </dt>
          <dd>
            {calendarioEntity.terminacionOficinal ? (
              <TextFormat value={calendarioEntity.terminacionOficinal} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="lab3FabricaApp.calendario.facultad">Facultad</Translate>
          </dt>
          <dd>{calendarioEntity.facultad ? calendarioEntity.facultad.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/calendario" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/calendario/${calendarioEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CalendarioDetail;
