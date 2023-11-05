/* eslint-disable */
import { Container, Dropdown } from "react-bootstrap";
import CardComponent from "../../components/ui/Card";
import { useEffect, useState } from "react";
import { getExploreProjects } from "../../services/ProjectService";
import { useSelector } from "react-redux";
import { selectUser } from "../../redux/userSlice";
import React from "react";

const Explore = () => {
    const userDataStore = useSelector(selectUser);
    const [exploreProjects, setExploreProjects] = useState([]);

    const handleConnectionClick = async (level: any) => {
        let response = await getExploreProjects(level, userDataStore.token);
        if (response?.status === 200) {
            setExploreProjects(response?.data);
        }
    }

    useEffect(() => {
        handleConnectionClick(1);
    }, []);

    return (
        <>
            <Container className="mt-3">
                <div className="d-flex flex-row my-2 justify-content-between py-3">
                    <h1> Explore New Projects </h1>

                    <Dropdown>
                        <Dropdown.Toggle variant="dark" id="dropdown-basic">
                            Search Project
                        </Dropdown.Toggle>

                        <Dropdown.Menu>
                            <Dropdown.Item onClick={() => handleConnectionClick(1)}>1st Connection</Dropdown.Item>
                            <Dropdown.Item onClick={() => handleConnectionClick(2)}>2nd Connection</Dropdown.Item>
                            <Dropdown.Item onClick={() => handleConnectionClick(3)}>3rd Connection</Dropdown.Item>
                        </Dropdown.Menu>
                    </Dropdown>
                </div>

                <div className="d-flex flex-wrap">
                    {exploreProjects.map((project: any, idx) => (
                        <CardComponent key={idx} repositoryName={project.repositoryName} repositoryOwner={project.repositoryOwner} />
                    ))}
                </div>
            </Container>
        </>
    )
}

export default Explore;
