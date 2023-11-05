import { MDBDataTableV5 } from "mdbreact";
import { useEffect, useMemo, useState } from "react";
import { Badge, Button, Container, Table } from "react-bootstrap"
import AddContributorModal from "./AddContributorModal";
import { getProject, getProjectContributors } from "../../services/ProjectService";
import { useSelector } from "react-redux";
import { selectUser } from "../../redux/userSlice";
import React from "react";

const ProjectDetails = () => {
    const [openAddContributorModal, setAddContributorModal] = useState(false);
    const userDataStore = useSelector(selectUser);
    const [projectDetails, setProjectDetails] = useState({} as any);
    const [contributorsList, setContributorsList] = useState([]);
    const projectId = window.location.pathname.split("/").pop();

    const handleModalClose = () => {
        setAddContributorModal(false);
    };

    const handleModalOpen = () => {
        setAddContributorModal(true);
    }

    const rows = useMemo(() => {
        return contributorsList.map((item: any) => {
            return { contributorName: item.username, contributorRole: <>{projectDetails.userId === item.userId ? (<Badge bg="info">Owner</Badge>) : (<Badge bg="primary">Contributor</Badge>)}</> }
        })
    }, [contributorsList, projectDetails.userId])

    useEffect(() => {
        async function fetchProjectData() {
            let response = await getProject(projectId, userDataStore.token);
            if (response?.status === 200) {
                setProjectDetails(response?.data);
            }
            let contributorResponse = await getProjectContributors(projectId, userDataStore.token);
            if (contributorResponse?.status === 200) {
                setContributorsList(contributorResponse?.data?.body);
            }
        }
        fetchProjectData()
    }, [projectId, userDataStore.token])

    const columns = [
        {
            label: 'Contributor Name',
            field: 'contributorName',
        },
        {
            label: "Role",
            field: 'contributorRole',
        }
    ]
    return (
        <>
            <Container className="mt-3">
                <div className="details">
                    <h1 className='py-3' >Project Flow Details</h1>
                    <div className="w-100">
                        <Table striped hover>
                            <tbody>
                                <tr>
                                    <td>Repository:</td>
                                    <td><a target="_blank" href={`https://www.github.com/${projectDetails.repositoryOwner}/${projectDetails.repositoryName}`} rel="noreferrer">{'Gitcolab'} <i className="fa-solid fa-up-right-from-square"></i></a></td>
                                </tr>
                                <tr>
                                    <td>JIRA Board:</td>
                                    <td>
                                        {projectDetails.jiraBoardName !== "" ? (
                                            <a target="_blank" href="https://www.github.com" rel="noreferrer">{'Gitcolab JIRA'} <i className="fa-solid fa-up-right-from-square"></i></a>
                                        ) : (
                                            <>JIRA BOARD NOT AVAILABLE</>
                                        )}
                                    </td>
                                </tr>
                                <tr>
                                    <td>Owner:</td>
                                    <td>{projectDetails.repositoryOwner}</td>
                                </tr>
                                <tr>
                                    <td>Total Participants:</td>
                                    <td>{contributorsList.length}</td>
                                </tr>
                            </tbody>
                        </Table>
                    </div>
                </div>
                <div>
                    <div className="d-flex flex-row my-2 justify-content-between py-3">
                        <h1>Contributors</h1>
                        {projectDetails.userId === userDataStore.id && (
                            <Button variant="dark" type="button" onClick={handleModalOpen}>Add Contributor</Button>
                        )}
                    </div>
                    <MDBDataTableV5 data={{ rows, columns }} bordered hover></MDBDataTableV5>
                </div>
                {openAddContributorModal && (
                    <AddContributorModal
                        handleClose={handleModalClose} contributorDetails={{ repositoryName: projectDetails.repositoryName }} />
                )}
            </Container>
        </>
    )
}

export default ProjectDetails;