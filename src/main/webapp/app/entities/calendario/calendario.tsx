import React, { useState, useEffect } from 'react';
import InfiniteScroll from 'react-infinite-scroll-component';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICalendario } from 'app/shared/model/calendario.model';
import { getEntities, reset } from './calendario.reducer';

export const Calendario = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );
  const [sorting, setSorting] = useState(false);

  const calendarioList = useAppSelector(state => state.calendario.entities);
  const loading = useAppSelector(state => state.calendario.loading);
  const totalItems = useAppSelector(state => state.calendario.totalItems);
  const links = useAppSelector(state => state.calendario.links);
  const entity = useAppSelector(state => state.calendario.entity);
  const updateSuccess = useAppSelector(state => state.calendario.updateSuccess);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      })
    );
  };

  const resetAll = () => {
    dispatch(reset());
    setPaginationState({
      ...paginationState,
      activePage: 1,
    });
    dispatch(getEntities({}));
  };

  useEffect(() => {
    resetAll();
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      resetAll();
    }
  }, [updateSuccess]);

  useEffect(() => {
    getAllEntities();
  }, [paginationState.activePage]);

  const handleLoadMore = () => {
    if ((window as any).pageYOffset > 0) {
      setPaginationState({
        ...paginationState,
        activePage: paginationState.activePage + 1,
      });
    }
  };

  useEffect(() => {
    if (sorting) {
      getAllEntities();
      setSorting(false);
    }
  }, [sorting]);

  const sort = p => () => {
    dispatch(reset());
    setPaginationState({
      ...paginationState,
      activePage: 1,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
    setSorting(true);
  };

  const handleSyncList = () => {
    resetAll();
  };

  return (
    <div>
      <h2 id="calendario-heading" data-cy="CalendarioHeading">
        <Translate contentKey="lab3FabricaApp.calendario.home.title">Calendarios</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="lab3FabricaApp.calendario.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/calendario/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="lab3FabricaApp.calendario.home.createLabel">Create new Calendario</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={calendarioList ? calendarioList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {calendarioList && calendarioList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="lab3FabricaApp.calendario.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('semestre')}>
                    <Translate contentKey="lab3FabricaApp.calendario.semestre">Semestre</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('publicacionOferta')}>
                    <Translate contentKey="lab3FabricaApp.calendario.publicacionOferta">Publicacion Oferta</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('inicioMatriculas')}>
                    <Translate contentKey="lab3FabricaApp.calendario.inicioMatriculas">Inicio Matriculas</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('finMatriculas')}>
                    <Translate contentKey="lab3FabricaApp.calendario.finMatriculas">Fin Matriculas</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('inicioAjustes')}>
                    <Translate contentKey="lab3FabricaApp.calendario.inicioAjustes">Inicio Ajustes</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('finAjustes')}>
                    <Translate contentKey="lab3FabricaApp.calendario.finAjustes">Fin Ajustes</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('inicioClases')}>
                    <Translate contentKey="lab3FabricaApp.calendario.inicioClases">Inicio Clases</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('finClases')}>
                    <Translate contentKey="lab3FabricaApp.calendario.finClases">Fin Clases</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('inicioExamenesFinales')}>
                    <Translate contentKey="lab3FabricaApp.calendario.inicioExamenesFinales">Inicio Examenes Finales</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('finExamenesFinales')}>
                    <Translate contentKey="lab3FabricaApp.calendario.finExamenesFinales">Fin Examenes Finales</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('inicioValidaciones')}>
                    <Translate contentKey="lab3FabricaApp.calendario.inicioValidaciones">Inicio Validaciones</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('finValidaciones')}>
                    <Translate contentKey="lab3FabricaApp.calendario.finValidaciones">Fin Validaciones</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('inicioHabilitaciones')}>
                    <Translate contentKey="lab3FabricaApp.calendario.inicioHabilitaciones">Inicio Habilitaciones</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('finHabilitaciones')}>
                    <Translate contentKey="lab3FabricaApp.calendario.finHabilitaciones">Fin Habilitaciones</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('terminacionOficinal')}>
                    <Translate contentKey="lab3FabricaApp.calendario.terminacionOficinal">Terminacion Oficinal</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="lab3FabricaApp.calendario.facultad">Facultad</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {calendarioList.map((calendario, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`/calendario/${calendario.id}`} color="link" size="sm">
                        {calendario.id}
                      </Button>
                    </td>
                    <td>{calendario.semestre}</td>
                    <td>
                      {calendario.publicacionOferta ? (
                        <TextFormat type="date" value={calendario.publicacionOferta} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>
                      {calendario.inicioMatriculas ? (
                        <TextFormat type="date" value={calendario.inicioMatriculas} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>
                      {calendario.finMatriculas ? (
                        <TextFormat type="date" value={calendario.finMatriculas} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>
                      {calendario.inicioAjustes ? (
                        <TextFormat type="date" value={calendario.inicioAjustes} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>
                      {calendario.finAjustes ? <TextFormat type="date" value={calendario.finAjustes} format={APP_DATE_FORMAT} /> : null}
                    </td>
                    <td>
                      {calendario.inicioClases ? <TextFormat type="date" value={calendario.inicioClases} format={APP_DATE_FORMAT} /> : null}
                    </td>
                    <td>
                      {calendario.finClases ? <TextFormat type="date" value={calendario.finClases} format={APP_DATE_FORMAT} /> : null}
                    </td>
                    <td>
                      {calendario.inicioExamenesFinales ? (
                        <TextFormat type="date" value={calendario.inicioExamenesFinales} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>
                      {calendario.finExamenesFinales ? (
                        <TextFormat type="date" value={calendario.finExamenesFinales} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>
                      {calendario.inicioValidaciones ? (
                        <TextFormat type="date" value={calendario.inicioValidaciones} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>
                      {calendario.finValidaciones ? (
                        <TextFormat type="date" value={calendario.finValidaciones} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>
                      {calendario.inicioHabilitaciones ? (
                        <TextFormat type="date" value={calendario.inicioHabilitaciones} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>
                      {calendario.finHabilitaciones ? (
                        <TextFormat type="date" value={calendario.finHabilitaciones} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>
                      {calendario.terminacionOficinal ? (
                        <TextFormat type="date" value={calendario.terminacionOficinal} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>{calendario.facultad ? <Link to={`/facultad/${calendario.facultad.id}`}>{calendario.facultad.id}</Link> : ''}</td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`/calendario/${calendario.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`/calendario/${calendario.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`/calendario/${calendario.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                          <FontAwesomeIcon icon="trash" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.delete">Delete</Translate>
                          </span>
                        </Button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          ) : (
            !loading && (
              <div className="alert alert-warning">
                <Translate contentKey="lab3FabricaApp.calendario.home.notFound">No Calendarios found</Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default Calendario;
