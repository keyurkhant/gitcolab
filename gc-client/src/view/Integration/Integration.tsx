import { useEffect, useMemo, useState } from "react";
import { Badge, Button, Container } from "react-bootstrap";
import { MDBDataTableV5 } from 'mdbreact';
import AdddProjectModal from "./AddProjectModal";
import { useNavigate } from "react-router";
import {
    ATLASSIAN_CLIENT_ID,
    ATLASSIAN_URL,
    GITHUB_CLIENT_ID,
    GITHUB_SCOPE
} from "../../credentials";
import { useDispatch, useSelector } from "react-redux";
import { login, selectUser } from "../../redux/userSlice";
import { getAtlassianAccessToken, getGithubAccessToken } from "../../services/AuthService";
import { getProjectList } from "../../services/ProjectService";
import React from "react";

const Integration = () => {
    const [githubAuthenticated, setGithubAuthenticated] = useState("");
    const [atlassianAuthenticated, setAtlassianAuthenticated] = useState("");
    const [openAddProjectModal, setOpenAddProjectModal] = useState(false);
    const clientId = GITHUB_CLIENT_ID;
    const scope = GITHUB_SCOPE;
    const navigate = useNavigate();
    const dispatch = useDispatch();
    const userDataStore = useSelector(selectUser);
    const atlassianURL = ATLASSIAN_URL;
    const atlassianClientId = ATLASSIAN_CLIENT_ID;
    const [projectList, setProjectList] = useState([]);

    const handleGithubLogin = () => {
        window.location.assign(`https://github.com/login/oauth/authorize?client_id=${clientId}&scope=${scope}`);
    }
    const handleAtlassianLogin = () => {
        window.location.assign(atlassianURL);
    }

    useEffect(() => {
        const url = new URL(window.location.href);
        const authenticationCode = url.search.replace("?code=", "");
        if (authenticationCode !== "" && authenticationCode.length <= 100 && githubAuthenticated === "") {
            const fetchData = async () => {
                const data = {
                    "email": userDataStore.email,
                    "code": authenticationCode
                };
                const response = await getGithubAccessToken(data, userDataStore.token);
                if (response?.data) {
                    const storeObj = {
                        ...userDataStore,
                        githubToken: response.data.token
                    };
                    dispatch(
                        login(storeObj)
                    )
                }
                setGithubAuthenticated(response.data.token);
                // setAtlassianAuthenticated("");
            }

            fetchData();
            navigate('/integration');
        }
        else if (authenticationCode.length > 100 && atlassianAuthenticated === '') {
            const fetchData = async () => {
                const data = {
                    "grant_type": "authorization_code",
                    "client_id": atlassianClientId,
                    "redirect_uri": "http://localhost:3000/integration",
                    "code": authenticationCode
                };
                const response = await getAtlassianAccessToken(data, userDataStore.token);
                console.log("From line 72");
                console.log(response);
                console.log(response.data);
                console.log("Done");
                if (response?.data) {
                    const storeObj = {
                        ...userDataStore,
                        atlassianToken: response?.data?.body?.access_token
                    };
                    dispatch(
                        login(storeObj)
                    )
                }
                setAtlassianAuthenticated(response?.data?.body?.access_token);
            }

            fetchData();
            navigate('/integration');
        }
    }, [atlassianAuthenticated, atlassianClientId, dispatch, githubAuthenticated, navigate, userDataStore, userDataStore.email, userDataStore.token]);

    const handleModalClose = () => {
        setOpenAddProjectModal(false);
    };

    const addProjectFlowHandler = () => {
        setOpenAddProjectModal(true);
    }

    // eslint-disable-next-line react-hooks/exhaustive-deps
    const handleClick = (id: any) => {
        navigate(`/integration/${id}`);
    }

    const columns = [
        {
            label: 'Project Name',
            field: 'projectName',
        },
        {
            label: "Role",
            field: 'projectRole',
        }

    ]

    const rows = useMemo(()=>{
        console.log("DATA===>", projectList);
        return projectList.map((item: any)=>{
            return{ projectName: item.repositoryName, projectRole: <>{item.userId === userDataStore.id ? (<Badge bg="info">Owner</Badge>) : (<Badge bg="primary">Contributor</Badge>)}</>, clickEvent: () => handleClick(item.id) }
        })
    },[handleClick, projectList, userDataStore.id])

    useEffect(() => {
        async function fetchProjectData() {
            let response = await getProjectList(userDataStore.token);
            if (response?.status === 200) {
                setProjectList(response?.data);
               
            }
        }
        fetchProjectData()

    }, [userDataStore.token])

    return (
        <>
            {(userDataStore.githubToken && userDataStore.atlassianToken) ? (
                <Container className="mt-3">
                    <div className="d-flex flex-row my-2 justify-content-between py-3">
                        <h1>Projects</h1>
                        <Button variant="dark" type="button" onClick={addProjectFlowHandler}>Add Project Flow</Button>
                    </div>
                    <MDBDataTableV5 data={{ rows, columns }} bordered hover></MDBDataTableV5>
                    {openAddProjectModal && (
                        <AdddProjectModal
                            handleClose={handleModalClose} projectDetails={{ repositoryName: "", isAtlassian: false, jiraBoard: "" }} />
                    )}
                </Container>
            ) : (
                <div style={{ display: "flex", alignItems: "center", justifyContent: "center", minHeight: "calc(100vh - 88px)", flexDirection: "column" }}>
                    <h4>{`You are not authenticated with github or atlassian.`}</h4>
                    <div className="d-flex">
                        {userDataStore.githubToken == null && (
                            <Button variant="dark" type="button" onClick={handleGithubLogin} className="mx-2">Add Github</Button>
                        )}
                        {userDataStore.atlassianToken == null && (
                            <Button variant="dark" type="button" onClick={handleAtlassianLogin} className="mx-2">Add Atlassian</Button>
                        )}

                    </div>
                </div>
            )}
        </>

    )
}

export default Integration;
