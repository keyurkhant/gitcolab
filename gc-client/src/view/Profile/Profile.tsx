import { Container, Col, Row } from 'react-bootstrap';
import UserProfile from '../../components/UserProfile/UserProfile';
import profileData from '../../model/ProfileData';
import { useSelector } from 'react-redux';
import { selectUser } from '../../redux/userSlice';
import React from 'react';

//sm = {12} md={6} lg = {4}

const Profile = () => {
    const userDataStore = useSelector(selectUser);
    return (
        <>
            <Container className="mt-3">
                <div className="d-flex flex-row my-2 justify-content-between py-3">
                    <h1>Welcome, {userDataStore.username}</h1>
                </div>
                <Row >
                    <div >
                        <div>
                            <Col >
                                <UserProfile {...profileData} />
                            </Col>
                        </div>
                    </div>

                </Row>

            </Container>
        </>
    )
}

export default Profile;